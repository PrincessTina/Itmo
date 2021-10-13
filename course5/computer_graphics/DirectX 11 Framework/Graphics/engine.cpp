#include "engine.h"

#include "../error.h"
#include "Components/component.h"

void Engine::InitGraphics(HWND hwnd, int width, int height)
{
	InitDirectX(hwnd, width, height);
	InitShaders();
	InitScene();
}

void Engine::RenderFrame()
{
	UINT stride = sizeof(Vertex);
	UINT offset = 0;
	float bgcolor[] = { 0.f, 0.1f,  0.1f, 1.0f };

	timer->SetTime();
	
	deviceContext->ClearRenderTargetView(renderTargetView, bgcolor);
	deviceContext->ClearDepthStencilView(depthStencilView, D3D11_CLEAR_DEPTH | D3D11_CLEAR_STENCIL, 1.0f, 0);

	deviceContext->IASetInputLayout(vertexShader.GetInputLayout());
	deviceContext->IASetPrimitiveTopology(D3D11_PRIMITIVE_TOPOLOGY::D3D10_PRIMITIVE_TOPOLOGY_TRIANGLELIST);
	deviceContext->RSSetState(rasterizerState);
	deviceContext->OMSetDepthStencilState(depthStencilState, 0);
	deviceContext->VSSetShader(vertexShader.GetShader(), NULL, 0);
	deviceContext->PSSetShader(pixelShader.GetShader(), NULL, 0);

	for (Component* component : components)
	{
		component->Draw(timer->GetTime());
	}

	swapChain->Present(1, NULL);
}

void Engine::InitDirectX(HWND hwnd, int width, int height)
{
	CreateDeviceAndSwapChain(hwnd, width, height);

	ID3D11Texture2D* backBuffer;
	HRESULT hr = swapChain->GetBuffer(0, IID_ID3D11Texture2D, (void**)&backBuffer);
	popAssert(FAILED(hr), hr);
	
	hr = device->CreateRenderTargetView(backBuffer, NULL, &renderTargetView);
	popAssert(FAILED(hr), hr);
	
	CreateDepthStencilView(width, height);
	deviceContext->OMSetRenderTargets(1, &renderTargetView, depthStencilView);
	CreateDepthStencilState();

	CreateViewport(width, height);
	CreateRasterizerState();
}

void Engine::CreateDeviceAndSwapChain(HWND hwnd, int width, int height)
{
	HRESULT hr;
	DXGI_SWAP_CHAIN_DESC swapDesc;
	ZeroMemory(&swapDesc, sizeof(DXGI_SWAP_CHAIN_DESC));

	swapDesc.BufferDesc.Width = width;
	swapDesc.BufferDesc.Height = height;
	swapDesc.BufferDesc.RefreshRate.Numerator = 60;
	swapDesc.BufferDesc.RefreshRate.Denominator = 1;
	swapDesc.BufferDesc.Format = DXGI_FORMAT_R8G8B8A8_UNORM;
	swapDesc.BufferDesc.ScanlineOrdering = DXGI_MODE_SCANLINE_ORDER_UNSPECIFIED;
	swapDesc.BufferDesc.Scaling = DXGI_MODE_SCALING_UNSPECIFIED;

	swapDesc.SampleDesc.Count = 1;
	swapDesc.SampleDesc.Quality = 0;

	swapDesc.BufferUsage = DXGI_USAGE_RENDER_TARGET_OUTPUT;
	swapDesc.BufferCount = 1; //2
	swapDesc.OutputWindow = hwnd;
	swapDesc.Windowed = true;
	swapDesc.SwapEffect = DXGI_SWAP_EFFECT_DISCARD; //DXGI_SWAP_EFFECT_FLIP_DISCARD
	swapDesc.Flags = DXGI_SWAP_CHAIN_FLAG_ALLOW_MODE_SWITCH;

	//D3D_FEATURE_LEVEL featureLevel[] = { D3D_FEATURE_LEVEL_11_1 };
	hr = D3D11CreateDeviceAndSwapChain(
		NULL,
		D3D_DRIVER_TYPE_HARDWARE,
		NULL,
		NULL, //D3D11_CREATE_DEVICE_DEBUG,
		NULL, //featureLevel,
		0, //1,
		D3D11_SDK_VERSION,
		&swapDesc,
		&swapChain,
		&device,
		NULL,
		&deviceContext);
	popAssert(FAILED(hr), hr);
}

void Engine::CreateDepthStencilView(int width, int height)
{
	D3D11_TEXTURE2D_DESC depthStencilDesc;
	depthStencilDesc.Width = width;
	depthStencilDesc.Height = height;
	depthStencilDesc.MipLevels = 1;
	depthStencilDesc.ArraySize = 1;
	depthStencilDesc.Format = DXGI_FORMAT_D24_UNORM_S8_UINT;
	depthStencilDesc.SampleDesc.Count = 1;
	depthStencilDesc.SampleDesc.Quality = 0;
	depthStencilDesc.Usage = D3D11_USAGE_DEFAULT;
	depthStencilDesc.BindFlags = D3D11_BIND_DEPTH_STENCIL;
	depthStencilDesc.CPUAccessFlags = 0;
	depthStencilDesc.MiscFlags = 0;

	HRESULT hr = device->CreateTexture2D(&depthStencilDesc, NULL, &depthStencilBuffer);
	popAssert(FAILED(hr), hr);

	hr = device->CreateDepthStencilView(depthStencilBuffer, NULL, &depthStencilView);
	popAssert(FAILED(hr), hr);
}

void Engine::CreateDepthStencilState()
{
	D3D11_DEPTH_STENCIL_DESC depthStencilDesc;
	ZeroMemory(&depthStencilDesc, sizeof(D3D11_DEPTH_STENCIL_DESC));

	depthStencilDesc.DepthEnable = true;
	depthStencilDesc.DepthWriteMask = D3D11_DEPTH_WRITE_MASK::D3D11_DEPTH_WRITE_MASK_ALL;
	depthStencilDesc.DepthFunc = D3D11_COMPARISON_FUNC::D3D11_COMPARISON_LESS_EQUAL;

	HRESULT hr = device->CreateDepthStencilState(&depthStencilDesc, &depthStencilState);
	popAssert(FAILED(hr), hr);
}

void Engine::CreateViewport(int width, int height)
{
	D3D11_VIEWPORT viewport;
	ZeroMemory(&viewport, sizeof(D3D11_VIEWPORT));
	
	viewport.Width = width;
	viewport.Height = height;
	viewport.TopLeftX = 0;
	viewport.TopLeftY = 0;
	viewport.MinDepth = 0;
	viewport.MaxDepth = 1.0f;

	deviceContext->RSSetViewports(1, &viewport);
}

void Engine::CreateRasterizerState()
{
	D3D11_RASTERIZER_DESC rasterizerDesc;
	ZeroMemory(&rasterizerDesc, sizeof(D3D11_RASTERIZER_DESC));

	rasterizerDesc.FillMode = D3D11_FILL_SOLID;
	rasterizerDesc.CullMode = D3D11_CULL_NONE;

	HRESULT hr = device->CreateRasterizerState(&rasterizerDesc, &rasterizerState);
	popAssert(FAILED(hr), hr);
}

void Engine::InitShaders()
{
	D3D11_INPUT_ELEMENT_DESC layoutDesc[] =
	{
		{"POSITION", 0, DXGI_FORMAT_R32G32B32_FLOAT, 0, 0, D3D11_INPUT_PER_VERTEX_DATA, 0},
		{"COLOR", 0, DXGI_FORMAT_R32G32B32_FLOAT, 0, D3D11_APPEND_ALIGNED_ELEMENT, D3D11_INPUT_PER_VERTEX_DATA, 0}
	};

	vertexShader.Init(device, L"x64\\Debug\\vertexshader.cso", layoutDesc, ARRAYSIZE(layoutDesc));
	pixelShader.Init(device, L"x64\\Debug\\pixelshader.cso");
}

void Engine::InitScene()
{
	timer = new Timer();
	components[0] = new Component();

	for (Component* component : components)
	{
		component->Init(device, deviceContext);
	}
}

// INPUT ASSEMBLER - InputLayout
// VERTEX SHADER - Vertex Shader
// RASTERIZER - Viewport
// PIXEL SHADER - Pixel Shader
// OUTPUT MERGER - Render Target View
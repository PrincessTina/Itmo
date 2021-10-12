#include "engine.h"

#include "../error.h"
#include "Shaders/vertex.h"

void Engine::InitGraphics(HWND hwnd)
{
	InitDirectX(hwnd);
	InitShaders();
	InitScene();
}

void Engine::RenderFrame()
{
	UINT stride = sizeof(Vertex);
	UINT offset = 0;
	float bgcolor[] = { 0.f, 0.1f,  0.1f, 1.0f };
	
	deviceContext->ClearRenderTargetView(renderTargetView, bgcolor);

	deviceContext->IASetInputLayout(vertexShader.GetInputLayout());
	deviceContext->IASetPrimitiveTopology(D3D11_PRIMITIVE_TOPOLOGY::D3D10_PRIMITIVE_TOPOLOGY_TRIANGLELIST);
	
	deviceContext->IASetVertexBuffers(0, 1, &vertexBuffer, &stride, &offset);
	deviceContext->VSSetShader(vertexShader.GetShader(), NULL, 0);
	deviceContext->PSSetShader(pixelShader.GetShader(), NULL, 0);

	deviceContext->Draw(3, 0);

	swapChain->Present(1, NULL);
}

void Engine::InitDirectX(HWND hwnd, int width, int height)
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

	ID3D11Texture2D* backBuffer;
	hr = swapChain->GetBuffer(0, IID_ID3D11Texture2D, (void**)&backBuffer);
	popAssert(FAILED(hr), hr);
	
	hr = device->CreateRenderTargetView(backBuffer, NULL, &renderTargetView);
	popAssert(FAILED(hr), hr);

	//swapChain->QueryInterface<IDXGISwapChain1>(&swapChain1);

	//context->QueryInterface(IID_ID3DUserDefinedAnnotation, (void**)&annotation);

	//ID3D11Debug* debug;
	//device->QueryInterface(IID_ID3D11Debug, (void**)&debug);
	
	deviceContext->OMSetRenderTargets(1, &renderTargetView, NULL);

	//Create the Viewport
	D3D11_VIEWPORT viewport;
	ZeroMemory(&viewport, sizeof(D3D11_VIEWPORT));

	viewport.Width = width;
	viewport.Height = height;
	viewport.TopLeftX = 0;
	viewport.TopLeftY = 0;
	viewport.MinDepth = 0;
	viewport.MaxDepth = 1.0f;
	
	//Set the Viewport
	deviceContext->RSSetViewports(1, &viewport);
}

void Engine::InitShaders()
{
	D3D11_INPUT_ELEMENT_DESC layoutDesc[] =
	{
		{"POSITION", 0, DXGI_FORMAT_R32G32_FLOAT, 0, 0, D3D11_INPUT_PER_VERTEX_DATA, 0},
		{"COLOR", 0, DXGI_FORMAT_R32G32B32_FLOAT, 0, D3D11_APPEND_ALIGNED_ELEMENT, D3D11_INPUT_PER_VERTEX_DATA, 0}
	};

	vertexShader.Init(device, L"x64\\Debug\\vertexshader.cso", layoutDesc, ARRAYSIZE(layoutDesc));
	pixelShader.Init(device, L"x64\\Debug\\pixelshader.cso");
}

void Engine::InitScene()
{
	Vertex vertexes[] =
	{
		Vertex(-0.5f, -0.5f, 1.f, 0.f, 0.f),
		Vertex(0.f, 0.5f, 0.f, 1.f, 0.f),
		Vertex(0.5f, -0.5f, 0.f, 0.f, 1.f),
	};

	D3D11_BUFFER_DESC vertexBufferDesc;
	ZeroMemory(&vertexBufferDesc, sizeof(vertexBufferDesc));

	vertexBufferDesc.Usage = D3D11_USAGE_DEFAULT;
	vertexBufferDesc.BindFlags = D3D11_BIND_VERTEX_BUFFER;
	vertexBufferDesc.CPUAccessFlags = 0;
	vertexBufferDesc.MiscFlags = 0;
	//vertexBufferDesc.StructureByteStride = 0;
	vertexBufferDesc.ByteWidth = sizeof(Vertex) * ARRAYSIZE(vertexes);

	D3D11_SUBRESOURCE_DATA vertexBufferData;
	ZeroMemory(&vertexBufferData, sizeof(vertexBufferData));
	
	vertexBufferData.pSysMem = vertexes;
	//vertexBufferData.SysMemPitch = 0;
	//vertexBufferData.SysMemSlicePitch = 0;

	HRESULT hr = device->CreateBuffer(&vertexBufferDesc, &vertexBufferData, &vertexBuffer);
	popAssert(FAILED(hr), hr);
}

// INPUT ASSEMBLER - InputLayout
// VERTEX SHADER - Vertex Shader
// RASTERIZER - Viewport
// PIXEL SHADER - Pixel Shader
// OUTPUT MERGER - Render Target View
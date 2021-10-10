#include "game.h"

#pragma comment(lib,"d3d11.lib")
#include "../error.h"

int WINAPI main(HINSTANCE hInstance, HINSTANCE hPrevInstance, PSTR pScmdline, int iCmdshow)
{
	Game game;
	game.Run(hInstance);

	return 0;
}

void Game::Run(HINSTANCE hInstance)
{
	Window mainWindow;
	mainWindow.Create(hInstance);

	InitializeDirectX(mainWindow.GetHandle());
	
	while (mainWindow.ProcessMessages()) 
	{
		RenderFrame();
	}
}

void Game::InitializeDirectX(HWND hwnd, int width, int height)
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
	swapDesc.BufferCount = 2;
	swapDesc.OutputWindow = hwnd;
	swapDesc.Windowed = true;
	swapDesc.SwapEffect = DXGI_SWAP_EFFECT_FLIP_DISCARD;
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
}

void Game::RenderFrame()
{
	float bgcolor[] = { 0.f, 0.1f,  0.1f, 1.0f };
	deviceContext->ClearRenderTargetView(renderTargetView, bgcolor);
	swapChain->Present(1, NULL);
}
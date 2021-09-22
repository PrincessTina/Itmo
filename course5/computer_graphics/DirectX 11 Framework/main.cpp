#pragma warning(disable : 4267)

#define WIN32_LEAN_AND_MEAN
#include "windows.h"
#include <iostream>
#include "winuser.h"
#include <wrl.h>

#pragma comment(lib, "d3d11.lib")
#pragma comment(lib, "dxgi.lib")
#pragma comment(lib, "d3dcompiler.lib")
#pragma comment(lib, "dxguid.lib")

#include <d3d.h>
#include <d3d11.h>
#include <d3d11_1.h>
#include <d3d11_4.h>
#include <d3dcompiler.h>
#include <directxmath.h>
#include <DirectXColors.h>
#include <chrono>
#include <thread>

#define ZCHECK(exp) if(FAILED(exp)) { printf("Check failed at file: %s at line %i", __FILE__, __LINE__); return 0; }

using namespace DirectX;


ID3D11DeviceContext* context;
IDXGISwapChain* swapChain;
IDXGISwapChain1* swapChain1;
ID3D11RenderTargetView* rtv;
ID3DUserDefinedAnnotation* annotation;

std::chrono::time_point<std::chrono::steady_clock> PrevTime;
float totalTime = 0;
unsigned int frameCount = 0;


LRESULT CALLBACK WndProc(HWND hwnd, UINT umessage, WPARAM wparam, LPARAM lparam)
{
	//auto id = std::this_thread::get_id();
	//std::cout << "WndProc id: " << id << "\n";

	switch (umessage)
	{
		// Check if the window is being destroyed.
	case WM_DESTROY:
	case WM_CLOSE:
	{
		PostQuitMessage(0);
		return 0;
	}

	case WM_SIZE:
	{
		std::cout << "Width " << LOWORD(lparam) << " Height " << HIWORD(lparam) << std::endl;

		return 0;
	}

	// Check if a key has been pressed on the keyboard.
	case WM_KEYDOWN:
	{
		// If a key is pressed send it to the input object so it can record that state.
		std::cout << "Key: " << static_cast<unsigned int>(wparam) << std::endl;

		if (static_cast<unsigned int>(wparam) == 27) PostQuitMessage(0);
		return 0;
	}

	// Check if a key has been released on the keyboard.
	case WM_KEYUP:
	{
		// If a key is released then send it to the input object so it can unset the state for that key.
		return 0;
	}

	// All other messages pass to the message handler in the system class.
	default:
	{
		return DefWindowProc(hwnd, umessage, wparam, lparam);
	}
	}
}


int WINAPI mmain(HINSTANCE hInstance, HINSTANCE hPrevInstance, PSTR pScmdline, int iCmdshow)
{
#pragma region Window Initialization
	LPCWSTR applicationName;
	HWND hWnd;

	//HINSTANCE hLocInstance;
	// Get the instance of this application.
	//hLocInstance = GetModuleHandle(NULL);

	// Give the application a name.
	applicationName = L"Game";

	WNDCLASSEX wc;
	int posX, posY;

	// Setup the windows class with default settings.
	wc.style = CS_HREDRAW | CS_VREDRAW | CS_OWNDC;
	wc.lpfnWndProc = WndProc;
	wc.cbClsExtra = 0;
	wc.cbWndExtra = 0;
	wc.hInstance = hInstance;
	wc.hIcon = LoadIcon(nullptr, IDI_WINLOGO);
	wc.hIconSm = wc.hIcon;
	wc.hCursor = LoadCursor(nullptr, IDC_ARROW);
	wc.hbrBackground = static_cast<HBRUSH>(GetStockObject(BLACK_BRUSH));
	wc.lpszMenuName = nullptr;
	wc.lpszClassName = applicationName;
	wc.cbSize = sizeof(WNDCLASSEX);

	// Register the window class.
	RegisterClassEx(&wc);

	// Determine the resolution of the clients desktop screen.
	auto screenWidth = GetSystemMetrics(SM_CXSCREEN);
	auto screenHeight = GetSystemMetrics(SM_CYSCREEN);

	// If windowed then set it to 800x800 resolution.
	screenWidth = 800;
	screenHeight = 800;

	// Place the window in the middle of the screen.
	posX = (GetSystemMetrics(SM_CXSCREEN) - screenWidth) / 2;
	posY = (GetSystemMetrics(SM_CYSCREEN) - screenHeight) / 2;

	RECT windowRect = { 0, 0, static_cast<LONG>(screenWidth), static_cast<LONG>(screenHeight) };
	AdjustWindowRect(&windowRect, WS_OVERLAPPEDWINDOW, FALSE);

	auto dwStyle = WS_SYSMENU | WS_CAPTION | WS_MINIMIZEBOX | WS_THICKFRAME; // WS_CLIPSIBLINGS | WS_CLIPCHILDREN | WS_POPUP
	// Create the window with the screen settings and get the handle to it.
	hWnd = CreateWindowEx(WS_EX_APPWINDOW, applicationName, applicationName,
		dwStyle,
		posX, posY,
		windowRect.right - windowRect.left,
		windowRect.bottom - windowRect.top,
		nullptr, nullptr, hInstance, nullptr);


	ShowWindow(hWnd, SW_SHOW);
	SetForegroundWindow(hWnd);
	SetFocus(hWnd);

	ShowCursor(true);

#pragma endregion Window Initialization

#pragma region DirectX initialization
	HRESULT res;

	DXGI_SWAP_CHAIN_DESC swapDesc = {};
	swapDesc.BufferCount = 2;
	swapDesc.BufferDesc.Width = screenWidth;
	swapDesc.BufferDesc.Height = screenHeight;
	swapDesc.BufferDesc.Format = DXGI_FORMAT_R8G8B8A8_UNORM;
	swapDesc.BufferDesc.RefreshRate.Numerator = 60;
	swapDesc.BufferDesc.RefreshRate.Denominator = 1;
	swapDesc.BufferDesc.ScanlineOrdering = DXGI_MODE_SCANLINE_ORDER_UNSPECIFIED;
	swapDesc.BufferDesc.Scaling = DXGI_MODE_SCALING_UNSPECIFIED;
	swapDesc.BufferUsage = DXGI_USAGE_RENDER_TARGET_OUTPUT;
	swapDesc.OutputWindow = hWnd;
	swapDesc.Windowed = true;
	swapDesc.SwapEffect = DXGI_SWAP_EFFECT_FLIP_DISCARD;
	swapDesc.Flags = DXGI_SWAP_CHAIN_FLAG_ALLOW_MODE_SWITCH;
	swapDesc.SampleDesc.Count = 1;
	swapDesc.SampleDesc.Quality = 0;


	Microsoft::WRL::ComPtr<ID3D11Device> device;

	D3D_FEATURE_LEVEL featureLevel[] = { D3D_FEATURE_LEVEL_11_1 };
	res = D3D11CreateDeviceAndSwapChain(
		nullptr,
		D3D_DRIVER_TYPE_HARDWARE,
		nullptr,
		D3D11_CREATE_DEVICE_DEBUG,
		featureLevel,
		1,
		D3D11_SDK_VERSION,
		&swapDesc,
		&swapChain,
		&device,
		nullptr,
		&context);

	ZCHECK(res);

	ID3D11Texture2D* backTex;
	res = swapChain->GetBuffer(0, IID_ID3D11Texture2D, (void**)&backTex);	ZCHECK(res);
	res = device->CreateRenderTargetView(backTex, nullptr, &rtv);			ZCHECK(res);

	swapChain->QueryInterface<IDXGISwapChain1>(&swapChain1);

	context->QueryInterface(IID_ID3DUserDefinedAnnotation, (void**)&annotation);

	ID3D11Debug* debug;
	device->QueryInterface(IID_ID3D11Debug, (void**)&debug);

	//ID3D11Device5* device1 = nullptr;
	//device->QueryInterface(IID_ID3D11Device5, (void**)&device1);
	//
	//IDXGIDevice4* dxgiDevice4;
	//device1->QueryInterface(IID_IDXGIDevice4, (void**)&dxgiDevice4);
	////context->QueryInterface(IID_ID3D11DeviceContext1);
	//IDXGIAdapter* adapter = nullptr;
	//dxgiDevice4->GetAdapter(&adapter);
	//
	//IDXGIOutput* output = nullptr;
	//adapter->EnumOutputs(0, &output);
	//DXGI_OUTPUT_DESC outputDesc{};
	//output->GetDesc(&outputDesc);
	//
	//DXGI_ADAPTER_DESC adapterDesc{};
	//adapter->GetDesc(&adapterDesc);
	//
	//
	//IDXGIFactory5* factory = nullptr;
	//adapter->GetParent(IID_IDXGIFactory5, (void**)&factory);


	ID3DBlob* vertexBC;
	ID3DBlob* errorVertexCode;
	res = D3DCompileFromFile(L"MiniTri.fx",
		nullptr /*macros*/,
		nullptr /*include*/,
		"VSMain",
		"vs_5_0",
		D3DCOMPILE_DEBUG | D3DCOMPILE_SKIP_OPTIMIZATION,
		0,
		&vertexBC,
		&errorVertexCode);

	if (FAILED(res)) {
		// If the shader failed to compile it should have written something to the error message.
		if (errorVertexCode) {
			char* compileErrors = (char*)(errorVertexCode->GetBufferPointer());

			std::cout << compileErrors << std::endl;
		}
		// If there was  nothing in the error message then it simply could not find the shader file itself.
		else
		{
			MessageBox(hWnd, L"MiniTri.fx", L"Missing Shader File", MB_OK);
		}

		return 0;
	}


	D3D_SHADER_MACRO Shader_Macros[] = { "TEST", "1", "TCOLOR", "float4(0.0f, 1.0f, 0.0f, 1.0f)", nullptr, nullptr };

	ID3DBlob* pixelBC;
	ID3DBlob* errorPixelCode;
	res = D3DCompileFromFile(L"MiniTri.fx", Shader_Macros /*macros*/, nullptr /*include*/, "PSMain", "ps_5_0", D3DCOMPILE_DEBUG | D3DCOMPILE_SKIP_OPTIMIZATION, 0, &pixelBC, &errorPixelCode);

	if (FAILED(res)) {
		// If the shader failed to compile it should have written something to the error message.
		if (errorPixelCode) {
			char* compileErrors = (char*)(errorPixelCode->GetBufferPointer());

			std::cout << compileErrors << std::endl;
		}
		// If there was  nothing in the error message then it simply could not find the shader file itself.
		else
		{
			MessageBox(hWnd, L"MiniTri.fx", L"Missing Shader File", MB_OK);
		}

		return 0;
	}

	ID3D11VertexShader* vertexShader;
	ID3D11PixelShader* pixelShader;
	device->CreateVertexShader(
		vertexBC->GetBufferPointer(),
		vertexBC->GetBufferSize(),
		nullptr, &vertexShader);

	device->CreatePixelShader(
		pixelBC->GetBufferPointer(),
		pixelBC->GetBufferSize(),
		nullptr, &pixelShader);

	D3D11_INPUT_ELEMENT_DESC inputElements[] = {
		D3D11_INPUT_ELEMENT_DESC {
			"POSITION",
			0,
			DXGI_FORMAT_R32G32B32A32_FLOAT,
			0,
			0,
			D3D11_INPUT_PER_VERTEX_DATA,
			0},
		D3D11_INPUT_ELEMENT_DESC {
			"COLOR",
			0,
			DXGI_FORMAT_R32G32B32A32_FLOAT,
			0,
			D3D11_APPEND_ALIGNED_ELEMENT,
			D3D11_INPUT_PER_VERTEX_DATA,
			0}
	};

	ID3D11InputLayout* layout;
	device->CreateInputLayout(
		inputElements,
		2,
		vertexBC->GetBufferPointer(),
		vertexBC->GetBufferSize(),
		&layout);


	ID3D11InputLayout* layout2;

	D3D11_INPUT_ELEMENT_DESC inputElements2[] = {
		D3D11_INPUT_ELEMENT_DESC {"POSITION",	0, DXGI_FORMAT_R32G32B32A32_FLOAT, 0, 0, D3D11_INPUT_PER_VERTEX_DATA, 0},
		D3D11_INPUT_ELEMENT_DESC {"COLOR",	0, DXGI_FORMAT_R32G32B32A32_FLOAT, 1, 0, D3D11_INPUT_PER_VERTEX_DATA, 0}
	};
	device->CreateInputLayout(inputElements2, 2, vertexBC->GetBufferPointer(), vertexBC->GetBufferSize(), &layout2);


	DirectX::XMFLOAT4 points[8] = {
		XMFLOAT4(0.5f, 0.5f, 0.5f, 1.0f),		XMFLOAT4(1.0f, 0.0f, 0.0f, 1.0f),
		XMFLOAT4(-0.5f, -0.5f, 0.5f, 1.0f),	XMFLOAT4(0.0f, 0.0f, 1.0f, 1.0f),
		XMFLOAT4(0.5f, -0.5f, 0.5f, 1.0f),	XMFLOAT4(0.0f, 1.0f, 0.0f, 1.0f),
		XMFLOAT4(-0.5f, 0.5f, 0.5f, 1.0f),	XMFLOAT4(1.0f, 1.0f, 1.0f, 1.0f),
	};

	DirectX::XMFLOAT4 positions[] = {
		XMFLOAT4(0.5f, 0.5f, 0.5f, 1.0f),
		XMFLOAT4(-0.5f, -0.5f, 0.5f, 1.0f),
		XMFLOAT4(0.5f, -0.5f, 0.5f, 1.0f),
		XMFLOAT4(-0.5f, 0.5f, 0.5f, 1.0f),
	};

	DirectX::XMFLOAT4 colors[] = {
		XMFLOAT4(1.0f, 0.0f, 0.0f, 1.0f),
		XMFLOAT4(0.0f, 0.0f, 1.0f, 1.0f),
		XMFLOAT4(0.0f, 1.0f, 0.0f, 1.0f),
		XMFLOAT4(1.0f, 1.0f, 1.0f, 1.0f),
	};


	int indeces[] = { 0,1,2, 1,0,3 };

	D3D11_BUFFER_DESC dataBufDesc = {};
	dataBufDesc.Usage = D3D11_USAGE_DEFAULT;
	dataBufDesc.BindFlags = D3D11_BIND_VERTEX_BUFFER;
	dataBufDesc.CPUAccessFlags = 0;
	dataBufDesc.MiscFlags = 0;
	dataBufDesc.StructureByteStride = 0;
	dataBufDesc.ByteWidth = sizeof(XMFLOAT4) * std::size(positions);

	ID3D11Buffer* pb;
	ID3D11Buffer* cb;

	D3D11_SUBRESOURCE_DATA positionsData = {};
	positionsData.pSysMem = positions;
	positionsData.SysMemPitch = 0;
	positionsData.SysMemSlicePitch = 0;
	D3D11_SUBRESOURCE_DATA colorsData = {};
	colorsData.pSysMem = colors;
	colorsData.SysMemPitch = 0;
	colorsData.SysMemSlicePitch = 0;

	device->CreateBuffer(&dataBufDesc, &positionsData, &pb);
	device->CreateBuffer(&dataBufDesc, &colorsData, &cb);


	D3D11_BUFFER_DESC vertexBufDesc = {};
	vertexBufDesc.Usage = D3D11_USAGE_DEFAULT;
	vertexBufDesc.BindFlags = D3D11_BIND_VERTEX_BUFFER;
	vertexBufDesc.CPUAccessFlags = 0;
	vertexBufDesc.MiscFlags = 0;
	vertexBufDesc.StructureByteStride = 0;
	vertexBufDesc.ByteWidth = sizeof(XMFLOAT4) * std::size(points);

	D3D11_SUBRESOURCE_DATA vertexData = {};
	vertexData.pSysMem = points;
	vertexData.SysMemPitch = 0;
	vertexData.SysMemSlicePitch = 0;

	ID3D11Buffer* vb;
	device->CreateBuffer(&vertexBufDesc, &vertexData, &vb);


	D3D11_BUFFER_DESC indexBufDesc = {};
	indexBufDesc.Usage = D3D11_USAGE_DEFAULT;
	indexBufDesc.BindFlags = D3D11_BIND_INDEX_BUFFER;
	indexBufDesc.CPUAccessFlags = 0;
	indexBufDesc.MiscFlags = 0;
	indexBufDesc.StructureByteStride = 0;
	indexBufDesc.ByteWidth = sizeof(int) * std::size(indeces);

	D3D11_SUBRESOURCE_DATA indexData = {};
	indexData.pSysMem = indeces;
	indexData.SysMemPitch = 0;
	indexData.SysMemSlicePitch = 0;

	ID3D11Buffer* ib;
	device->CreateBuffer(&indexBufDesc, &indexData, &ib);


	ID3D11Buffer* vBuffers[] = { pb, cb };
	UINT strides[] = { 16, 16 };
	UINT offsets[] = { 0, 0 };

	context->IASetInputLayout(layout2);
	context->IASetPrimitiveTopology(D3D_PRIMITIVE_TOPOLOGY_TRIANGLELIST);
	context->IASetIndexBuffer(ib, DXGI_FORMAT_R32_UINT, 0);
	context->IASetVertexBuffers(0, 2, vBuffers, strides, offsets);
	context->VSSetShader(vertexShader, nullptr, 0);
	context->PSSetShader(pixelShader, nullptr, 0);


	CD3D11_RASTERIZER_DESC rastDesc = {};
	rastDesc.CullMode = D3D11_CULL_NONE;
	rastDesc.FillMode = D3D11_FILL_SOLID;

	ID3D11RasterizerState* rastState;
	res = device->CreateRasterizerState(&rastDesc, &rastState); ZCHECK(res);

	context->RSSetState(rastState);


	D3D11_VIEWPORT viewport = {};
	viewport.Width = static_cast<float>(screenWidth);
	viewport.Height = static_cast<float>(screenHeight);
	viewport.TopLeftX = 0;
	viewport.TopLeftY = 0;
	viewport.MinDepth = 0;
	viewport.MaxDepth = 1.0f;

	context->RSSetViewports(1, &viewport);
	context->OMSetRenderTargets(1, &rtv, nullptr);

#pragma endregion DirectX initialization

	MSG msg = {};

	// Loop until there is a quit message from the window or the user.
	bool isExitRequested = false;
	while (!isExitRequested) {
		// Handle the windows messages.
		while (PeekMessage(&msg, nullptr, 0, 0, PM_REMOVE)) {
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}

		// If windows signals to end the application then exit out.
		if (msg.message == WM_QUIT) {
			isExitRequested = true;
		}

		//auto id = std::this_thread::get_id();
		//std::cout << "Main id: " << id << "\n";

#pragma region DrawSomeStaff

		auto	curTime = std::chrono::steady_clock::now();
		float	deltaTime = std::chrono::duration_cast<std::chrono::microseconds>(curTime - PrevTime).count() / 1000000.0f;
		PrevTime = curTime;

		totalTime += deltaTime;
		frameCount++;


		if (totalTime > 1.0f) {
			float fps = frameCount / totalTime;

			totalTime = 0.0f;

			WCHAR text[256];
			swprintf_s(text, TEXT("FPS: %f"), fps);
			SetWindowText(hWnd, text);

			frameCount = 0;
		}

		float color[] = { totalTime, 0.1f, 0.1f, 1.0f };

		context->OMSetRenderTargets(1, &rtv, nullptr);

		context->ClearRenderTargetView(rtv, color);

		annotation->BeginEvent(L"BeginDraw");
		context->DrawIndexed(6, 0, 0);
		annotation->EndEvent();

		//bool s_EnableVSync = true;
		//UINT PresentInterval = s_EnableVSync ? std::min(4, (int)(s_FrameTime * 60.0f)) : 0;

		swapChain1->Present(1, /*DXGI_PRESENT_DO_NOT_WAIT*/ 0);
#pragma endregion DrawSomeStaff
	}

	vertexShader->Release();
	pixelShader->Release();

	device->Release();

	debug->ReportLiveDeviceObjects(D3D11_RLDO_DETAIL);

	return 0;
}

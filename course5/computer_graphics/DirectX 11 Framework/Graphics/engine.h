#pragma once
#include <d3d11.h>
#include "Shaders/shader.h"
#include "Shaders/vertex.h"
#include "Components/component.h"
#include "timer.h"

class Engine
{
public:
	void InitGraphics(HWND hwnd, int width, int height);
	void RenderFrame();
private:
	IDXGISwapChain* swapChain;
	ID3D11Device* device;
	ID3D11DeviceContext* deviceContext;
	ID3D11RenderTargetView* renderTargetView;
	
	VertexShader vertexShader;
	PixelShader pixelShader;

	ID3D11DepthStencilView* depthStencilView;
	ID3D11Texture2D* depthStencilBuffer;
	ID3D11DepthStencilState* depthStencilState;

	ID3D11RasterizerState* rasterizerState;

	Timer* timer;
	Component* components[2];

	void InitDirectX(HWND hwnd, int width, int height);
	void CreateDeviceAndSwapChain(HWND hwnd, int width, int height);
	void CreateDepthStencilView(int width, int height);
	void CreateDepthStencilState();
	void CreateViewport(int width, int height);
	void CreateRasterizerState();
	void InitShaders();
	void InitScene(int width, int height);
};

#pragma once
#include <d3d11.h>
#include "Shaders/shader.h"

class Engine
{
public:
	void InitGraphics(HWND hwnd);
	void RenderFrame();
private:
	IDXGISwapChain* swapChain;
	ID3D11Device* device;
	ID3D11DeviceContext* deviceContext;
	ID3D11RenderTargetView* renderTargetView;
	
	VertexShader vertexShader;
	PixelShader pixelShader;

	ID3D11Buffer* vertexBuffer;

	void InitDirectX(HWND hwnd, int width = 600, int height = 600);
	void InitShaders();
	void InitScene();
};

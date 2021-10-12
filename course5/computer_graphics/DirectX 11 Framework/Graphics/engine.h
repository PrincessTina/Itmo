#pragma once
#include <d3d11.h>
#include "Shaders/shader.h"

#include "Shaders/vertex.h"
#include "Buffers/buffer.h"

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

	Buffer<Vertex> vertexBuffer;
	Buffer<DWORD> indexBuffer;

	ID3D11DepthStencilView* depthStencilView;
	ID3D11Texture2D* depthStencilBuffer;
	ID3D11DepthStencilState* depthStencilState;

	ID3D11RasterizerState* rasterizerState;

	void InitDirectX(HWND hwnd, int width, int height);
	void CreateDeviceAndSwapChain(HWND hwnd, int width, int height);
	void CreateDepthStencilView(int width, int height);
	void CreateDepthStencilState();
	void CreateViewport(int width, int height);
	void CreateRasterizerState();
	void InitShaders();
	void InitScene();
	void CreateBuffer(ID3D11Buffer** bufferAddress, const void* bufferDataArray, UINT size, UINT flags);
};

#pragma once
#include <d3d11.h>
#include "window.h"

class Game
{
public:
	void Run(HINSTANCE hInstance);
private:
	IDXGISwapChain* swapChain;
	ID3D11Device* device;
	ID3D11DeviceContext* deviceContext;
	ID3D11RenderTargetView* renderTargetView;

	void InitializeDirectX(HWND hwnd, int width = 600, int height = 600);
	void RenderFrame();
};

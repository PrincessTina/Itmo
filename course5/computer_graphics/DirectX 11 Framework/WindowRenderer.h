#pragma once
#include <Windows.h>

class WindowRenderer
{
public:
	void Init(HINSTANCE hInstance, LPCWSTR applicationName = L"Game", int width = 800, int height = 800);
private:
	HWND m_Hwnd = NULL; // Handle to this window
	int m_Width = 0;
	int m_Height = 0;
};
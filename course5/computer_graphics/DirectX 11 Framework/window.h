#pragma once
#include <Windows.h>

class Window
{
public:
	~Window();
	void Init(HINSTANCE hInstance, int width = 600, int height = 600, LPCWSTR title = L"Game", LPCWSTR className = L"Game");
	bool ProcessMessages(); // Handle the windows messages

private:
	HWND m_Handle = NULL; // Handle to this window
	HINSTANCE m_ApplicationHandle = NULL;
	LPCWSTR m_ClassName;
};
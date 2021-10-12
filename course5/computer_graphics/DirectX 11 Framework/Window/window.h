#pragma once
#include <Windows.h>

class Window
{
public:
	~Window();
	inline HWND GetHandle() { return m_Handle; };
	void Create(HINSTANCE hInstance, int width, int height, LPCWSTR title = L"Game", LPCWSTR className = L"Game");
	bool ProcessMessages(); // Handle the windows messages

private:
	HWND m_Handle = NULL; // Handle to this window
	HINSTANCE m_ApplicationHandle = NULL;
	LPCWSTR m_ClassName;
};
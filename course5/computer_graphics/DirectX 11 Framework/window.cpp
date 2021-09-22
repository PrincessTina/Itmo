#include "window.h"

Window::~Window()
{
	if (m_Handle != NULL)
	{
		UnregisterClass(m_ClassName, m_ApplicationHandle);
		DestroyWindow(m_Handle);
	}
}

void Window::Init(HINSTANCE hInstance, int width, int height, LPCWSTR title, LPCWSTR className)
{
	// Setup the windows class with default settings.
	WNDCLASSEX wc;
	wc.style = CS_HREDRAW | CS_VREDRAW | CS_OWNDC;
	wc.lpfnWndProc = DefWindowProc; // WndProc
	wc.cbClsExtra = 0;
	wc.cbWndExtra = 0;
	wc.hInstance = hInstance;
	wc.hIcon = LoadIcon(NULL, IDI_WINLOGO);
	wc.hIconSm = wc.hIcon;
	wc.hCursor = LoadCursor(NULL, IDC_ARROW);
	wc.hbrBackground = static_cast<HBRUSH>(GetStockObject(BLACK_BRUSH));
	wc.lpszMenuName = NULL;
	wc.lpszClassName = className;
	wc.cbSize = sizeof(WNDCLASSEX);
	RegisterClassEx(&wc);

	// Place the window in the middle of the screen.
	int posX = (GetSystemMetrics(SM_CXSCREEN) - width) / 2;
	int posY = (GetSystemMetrics(SM_CYSCREEN) - height) / 2;

	RECT windowRect = { 0, 0, static_cast<LONG>(width), static_cast<LONG>(height) };
	AdjustWindowRect(&windowRect, WS_OVERLAPPEDWINDOW, FALSE);

	// Create the window with the screen settings and get the handle to it.
	m_Handle = CreateWindowEx(WS_EX_APPWINDOW,
		className,
		title,
		WS_CAPTION | WS_MINIMIZEBOX | WS_SYSMENU | WS_THICKFRAME,
		posX, 
		posY,
		windowRect.right - windowRect.left,
		windowRect.bottom - windowRect.top,
		NULL,
		NULL,
		hInstance, 
		nullptr);

	ShowWindow(m_Handle, SW_SHOW);
	SetForegroundWindow(m_Handle);
	SetFocus(m_Handle);
	ShowCursor(true);

	m_ApplicationHandle = hInstance;
	m_ClassName = className;
}

bool Window::ProcessMessages()
{
	MSG msg;
	ZeroMemory(&msg, sizeof(MSG)); 

	if (PeekMessage(&msg, m_Handle, 0, 0, PM_REMOVE))
	{
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

	// Check if the window was closed
	if (msg.message == WM_NULL && !IsWindow(m_Handle))
	{
		m_Handle = NULL; // Message processing loop takes care of destroying this window
		UnregisterClass(m_ClassName, m_ApplicationHandle);
		return false;
	}

	return true;
}
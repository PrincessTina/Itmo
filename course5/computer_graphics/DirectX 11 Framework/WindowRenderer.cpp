#include "WindowRenderer.h"

void WindowRenderer::Init(HINSTANCE hInstance, LPCWSTR applicationName, int width, int height)
{
	// Setup the windows class with default settings.
	WNDCLASSEX wc;
	wc.style = CS_HREDRAW | CS_VREDRAW | CS_OWNDC;
	//wc.lpfnWndProc = WndProc;
	wc.cbClsExtra = 0;
	wc.cbWndExtra = 0;
	wc.hInstance = hInstance;
	wc.hIcon = LoadIcon(NULL, IDI_WINLOGO);
	wc.hIconSm = wc.hIcon;
	wc.hCursor = LoadCursor(NULL, IDC_ARROW);
	wc.hbrBackground = static_cast<HBRUSH>(GetStockObject(BLACK_BRUSH));
	wc.lpszMenuName = NULL;
	wc.lpszClassName = applicationName;
	wc.cbSize = sizeof(WNDCLASSEX);
	RegisterClassEx(&wc);

	// Place the window in the middle of the screen.
	int posX = (GetSystemMetrics(SM_CXSCREEN) - width) / 2;
	int posY = (GetSystemMetrics(SM_CYSCREEN) - height) / 2;

	RECT windowRect = { 0, 0, static_cast<LONG>(width), static_cast<LONG>(height) };
	AdjustWindowRect(&windowRect, WS_OVERLAPPEDWINDOW, FALSE);

	// Create the window with the screen settings and get the handle to it.
	m_Hwnd = CreateWindowEx(WS_EX_APPWINDOW, 
		applicationName, 
		applicationName,
		WS_CAPTION | WS_MINIMIZEBOX | WS_SYSMENU | WS_THICKFRAME,
		posX, 
		posY,
		windowRect.right - windowRect.left,
		windowRect.bottom - windowRect.top,
		NULL,
		NULL,
		hInstance, 
		nullptr);

	ShowWindow(m_Hwnd, SW_SHOW);
	SetForegroundWindow(m_Hwnd);
	SetFocus(m_Hwnd);

	ShowCursor(true);
}
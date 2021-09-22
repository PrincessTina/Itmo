#pragma comment(lib,"d3d11.lib")
#include "game.h"

int WINAPI main(HINSTANCE hInstance, HINSTANCE hPrevInstance, PSTR pScmdline, int iCmdshow)
{
	Game game;
	game.Run(hInstance);

	return 0;
}

void Game::Run(HINSTANCE hInstance)
{
	Window mainWindow;
	mainWindow.Init(hInstance);

	while (mainWindow.ProcessMessages()) {}
}
#include "game.h"

#include "../Window/window.h"
#include "../Graphics/engine.h"

int WINAPI main(HINSTANCE hInstance, HINSTANCE hPrevInstance, PSTR pScmdline, int iCmdshow)
{
	Game game;
	game.Run(hInstance);

	return 0;
}

void Game::Run(HINSTANCE hInstance)
{
	int width = 800;
	int height = 600;
	
	Window mainWindow;
	mainWindow.Create(hInstance, width, height);

	Engine engine;
	engine.InitGraphics(mainWindow.GetHandle(), width, height);

	while (mainWindow.ProcessMessages())
	{
		engine.RenderFrame();
	}
}
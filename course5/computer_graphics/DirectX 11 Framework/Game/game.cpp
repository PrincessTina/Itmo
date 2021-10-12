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
	Window mainWindow;
	mainWindow.Create(hInstance);

	Engine engine;
	engine.InitGraphics(mainWindow.GetHandle());

	//engine.RenderFrame();
	while (mainWindow.ProcessMessages())
	{
		engine.RenderFrame();
	}
}
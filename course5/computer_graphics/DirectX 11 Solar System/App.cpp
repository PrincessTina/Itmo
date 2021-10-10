#include "App.h"
#include "Melon.h"
#include "Pyramid.h"
#include "Box.h"
#include "Cylinder.h"
//#include "Sheet.h"
#include "SkinnedBox.h"
#include <memory>
#include <algorithm>
#include "NeededMath.h"
#include "Surface.h"
#include "GDIPlusManager.h"
#include "imgui.h"

namespace dx = DirectX;

GDIPlusManager gdipm;

App::App()
	:
	wnd(800, 600, "Graphics Framework"),
	light(wnd.Gfx())
{
	class Factory
	{
	public:
		Factory(Graphics& gfx)
			:
			gfx(gfx)
		{}
		std::unique_ptr<Drawable> operator()()
		{
			const DirectX::XMFLOAT3 mat = { cdist(rng),cdist(rng),cdist(rng) };
			switch (sdist(rng))
			{
				
				case 0:
					return std::make_unique<Box>(
						gfx, rng, adist, ddist,
						odist, rdist, bdist, mat
						);/*
				case 0:
					return std::make_unique<SkinnedBox>(
						gfx, rng, adist, ddist,
						odist, rdist
						);*/
				//не работает	
				/*case 0:
					return std::make_unique<Cylinder>(
						gfx, rng, adist, ddist, odist,
						rdist, bdist, tdist
						);
				//работает, но 50/50
				case 0:
					return std::make_unique<Pyramid>(
						gfx, rng, adist, ddist, odist,
						rdist, tdist
						);*/
			
			default:
				assert(false && "impossible drawable option in factory");
				return {};
			}
			
		}
	private:
		Graphics& gfx;
		std::mt19937 rng{ std::random_device{}() };
		std::uniform_int_distribution<int> sdist{ 0,0 };
		std::uniform_real_distribution<float> adist{ 0.0f,PI * 2.0f };
		std::uniform_real_distribution<float> ddist{ 0.0f,PI * 0.5f };
		std::uniform_real_distribution<float> odist{ 0.0f,PI * 0.08f };
		std::uniform_real_distribution<float> rdist{ 6.0f,20.0f };
		std::uniform_real_distribution<float> bdist{ 0.4f,3.0f };
		std::uniform_real_distribution<float> cdist{ 0.0f,1.0f };
		std::uniform_int_distribution<int> tdist{ 3,30 };
		/*std::uniform_int_distribution<int> latdist{ 5,20 };
		std::uniform_int_distribution<int> longdist{ 10,40 };
		std::uniform_int_distribution<int> typedist{ 0,4 };*/
	};

	drawables.reserve(nDrawables);
	std::generate_n(std::back_inserter(drawables), nDrawables, Factory{ wnd.Gfx() });

	wnd.Gfx().SetProjection(dx::XMMatrixPerspectiveLH(1.0f, 3.0f / 4.0f, 0.5f, 40.0f));
}

/*--------------Fourth Realization--------------*/
void App::DoFrame()
{
	const auto dt = timer.Mark() * speed_factor;

	wnd.Gfx().BeginFrame(0.07f, 0.0f, 0.12f);
	wnd.Gfx().SetCamera(cam.GetMatrix());
	light.Bind(wnd.Gfx(), cam.GetMatrix());

	for (auto& d : drawables)
	{
		d->Update(wnd.kbrd.KeyIsPressed(VK_SPACE) ? 0.0f : dt);
		d->Draw(wnd.Gfx());
	}
	light.Draw(wnd.Gfx());


	static char buffer[1024];

	/*--------------Imgui Window To Control Simulation Speed--------------*/
	if (ImGui::Begin("Simulation Speed"))
	{
		ImGui::SliderFloat("Speed Factor", &speed_factor, 0.0f, 6.0f, "%.4f", 3.2f);
		ImGui::Text("Application average %.3f ms/frame (%.1f FPS)", 1000.0f / ImGui::GetIO().Framerate, ImGui::GetIO().Framerate);
		ImGui::InputText("Input Text", buffer, sizeof(buffer));
		ImGui::Text("Status: %s", wnd.kbrd.KeyIsPressed(VK_SPACE) ? "PAUSED" : "RUNNING (hold spacebar to pause)");
	}
	ImGui::End();

	/*--------------Imgui Window To Control Сamera And Light--------------*/
	cam.SpawnControlWindow();
	light.SpawnControlWindow();

	/*--------------Present--------------*/
	wnd.Gfx().EndFrame();
}

App::~App()
{}

int App::Go()
{
	while (true)
	{
		if (const auto ecode = Window::ProcessMessages())
		{
			return *ecode;
		}
		DoFrame();
	}
}

/*--------------First Realization--------------*/
//void App::DoFrame()
//{
//	const float t = timer.Peek();
//	std::ostringstream oss;
//	oss << "Time elapsed : " << std::setprecision(1) << std::fixed << t << "s";
//	wnd.SetTitle(oss.str());
//}

/*--------------Second Realization--------------*/
//void App::DoFrame()
//{
//	/*--------------Start Blue Fun Test--------------*/
//	const float c = sin(timer.Peek()) / 2.0f + 0.5f;
//	wnd.Gfx().ClearBuffer(c, c, 1.0f);
//	/*--------------End Blue Fun Test--------------*/
//
//	/*--------------Godlike Triangles!!!--------------*/
//	//wnd.Gfx().DrawTestTriangle(0.0f); // Static triangles
//	wnd.Gfx().DrawTestTriangle(
//		-timer.Peek(),
//		0.0f,
//		0.0f
//	);
//	wnd.Gfx().DrawTestTriangle(
//		timer.Peek(),
//		wnd.mouse.GetPosX() / 400.0f - 1.0f,
//		-wnd.mouse.GetPosY() / 300.0f + 1.0f
//	); //Rotating Triangles
//		
//	wnd.Gfx().EndFrame();
//}

/*--------------Thierd Realization (On Boxes)--------------*/
//void App::DoFrame()
//{
//	auto dt = timer.Mark();
//	wnd.Gfx().ClearBuffer(0.07f, 0.0f, 0.12f);
//	for (auto& b : boxes)
//	{
//		b->Update(dt);
//		b->Draw(wnd.Gfx());
//	}
//	wnd.Gfx().EndFrame();
//}
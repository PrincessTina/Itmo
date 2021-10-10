#pragma once
#include <chrono>

class NeededTimer
{
public:
	NeededTimer();
	float Mark();
	float Peek() const;
private:
	std::chrono::steady_clock::time_point last;
};

#include "NeededTimer.h"

using namespace std::chrono;

NeededTimer::NeededTimer()
{
	last = steady_clock::now();
}

float NeededTimer::Mark()
{
	const auto old = last;
	last = steady_clock::now();
	const duration<float> frameTime = last - old;
	return frameTime.count();
}

float NeededTimer::Peek() const
{
	return duration<float>(steady_clock::now() - last).count();
}
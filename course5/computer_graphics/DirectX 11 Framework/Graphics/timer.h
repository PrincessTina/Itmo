#pragma once

class Timer
{
public:
	void SetTime() 
	{
		saved += 0.01f;
	};
	inline float GetTime() { return saved; };
private:
	float saved = 0;
};


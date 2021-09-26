#pragma once
#include <comdef.h>

inline void popAssert(bool condition, HRESULT hr) 
{ 
	if (condition)
	{
		_com_error error(hr);
		MessageBoxW(NULL, error.ErrorMessage(), L"Assert", MB_ICONWARNING);
	}
}

#pragma once
#include <comdef.h>

inline void popAssert(HRESULT hr) 
{ 
	_com_error error(hr);
	MessageBoxW(NULL, error.ErrorMessage(), L"Assert", MB_ICONWARNING);
}

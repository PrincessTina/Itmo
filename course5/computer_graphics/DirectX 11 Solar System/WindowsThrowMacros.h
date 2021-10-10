#pragma once

//Error Exception Helper Macro
#define MMWND_EXCEPT( hr ) Window::HrException( __LINE__,__FILE__,(hr) )
#define MMWND_LAST_EXCEPT() Window::HrException( __LINE__,__FILE__,GetLastError() )
#define MMWND_NOGFX_EXCEPT() Window::NoGfxException( __LINE__,__FILE__ )

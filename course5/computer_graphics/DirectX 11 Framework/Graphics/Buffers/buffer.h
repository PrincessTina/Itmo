#ifndef Buffer_h__
#define Buffer_h__

#include <d3d11.h>
#include "../error.h"

template<class T>
class Buffer
{
private:
	ID3D11Buffer* buffer;
	UINT stride;

public:
	Buffer() {}
	
	void CreateBuffer(T* bufferDataArray, UINT bufferDataArraySize, UINT flags, ID3D11Device* device)
	{
		stride = sizeof(T);
		
		D3D11_BUFFER_DESC bufferDesc;
		ZeroMemory(&bufferDesc, sizeof(bufferDesc));

		bufferDesc.Usage = D3D11_USAGE_DEFAULT;
		bufferDesc.ByteWidth = sizeof(T) * bufferDataArraySize;
		bufferDesc.BindFlags = flags;
		bufferDesc.CPUAccessFlags = 0;
		bufferDesc.MiscFlags = 0;
		//bufferDesc.StructureByteStride = 0;

		D3D11_SUBRESOURCE_DATA bufferData;
		ZeroMemory(&bufferData, sizeof(bufferData));

		bufferData.pSysMem = bufferDataArray;
		//bufferData.SysMemPitch = 0;
		//bufferData.SysMemSlicePitch = 0;

		HRESULT hr = device->CreateBuffer(&bufferDesc, &bufferData, &buffer);
		popAssert(FAILED(hr), hr);
	}

	ID3D11Buffer* Get() 
	{
		return buffer;
	}

	ID3D11Buffer** GetAddress() 
	{
		return &buffer;
	}

	const UINT* GetStride()
	{
		return &stride;
	}
};

#endif // Buffer_h__
#ifndef IndexBuffer_h__
#define IndexBuffer_h__

#include <d3d11.h>
#include "../error.h"

class IndexBuffer
{
private:
	ID3D11Buffer* buffer = nullptr;
	UINT size = 0;

public:
	IndexBuffer() {}

	void CreateBuffer(DWORD* indices, UINT indexCount, ID3D11Device* device)
	{
		size = indexCount;
		
		D3D11_BUFFER_DESC bufferDesc;
		ZeroMemory(&bufferDesc, sizeof(bufferDesc));

		bufferDesc.Usage = D3D11_USAGE_DEFAULT;
		bufferDesc.ByteWidth = sizeof(DWORD) * indexCount;
		bufferDesc.BindFlags = D3D11_BIND_INDEX_BUFFER;
		bufferDesc.CPUAccessFlags = 0;
		bufferDesc.MiscFlags = 0;
		//bufferDesc.StructureByteStride = 0;

		D3D11_SUBRESOURCE_DATA bufferData;
		ZeroMemory(&bufferData, sizeof(bufferData));

		bufferData.pSysMem = indices;
		//bufferData.SysMemPitch = 0;
		//bufferData.SysMemSlicePitch = 0;

		HRESULT hr = device->CreateBuffer(&bufferDesc, &bufferData, &buffer);
		popAssert(FAILED(hr), hr);
	}

	ID3D11Buffer* Get()
	{
		return buffer;
	}

	UINT GetSize() const
	{
		return size;
	}
};

#endif // IndexBuffer_h__
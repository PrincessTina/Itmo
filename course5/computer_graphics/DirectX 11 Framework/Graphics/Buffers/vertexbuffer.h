#ifndef VertexBuffer_h__
#define VertexBuffer_h__

#include <d3d11.h>
#include "../error.h"

template<class T>
class VertexBuffer
{
private:
	ID3D11Buffer* buffer = nullptr;
	UINT stride;

public:
	VertexBuffer() {}
	
	void CreateBuffer(T* vertexes, UINT vertexCount, ID3D11Device* device)
	{
		stride = sizeof(T);
		
		D3D11_BUFFER_DESC bufferDesc;
		ZeroMemory(&bufferDesc, sizeof(bufferDesc));

		bufferDesc.Usage = D3D11_USAGE_DEFAULT;
		bufferDesc.ByteWidth = sizeof(T) * vertexCount;
		bufferDesc.BindFlags = D3D11_BIND_VERTEX_BUFFER;
		bufferDesc.CPUAccessFlags = 0;
		bufferDesc.MiscFlags = 0;
		//bufferDesc.StructureByteStride = 0;

		D3D11_SUBRESOURCE_DATA bufferData;
		ZeroMemory(&bufferData, sizeof(bufferData));

		bufferData.pSysMem = vertexes;
		//bufferData.SysMemPitch = 0;
		//bufferData.SysMemSlicePitch = 0;

		HRESULT hr = device->CreateBuffer(&bufferDesc, &bufferData, &buffer);
		popAssert(FAILED(hr), hr);
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

#endif // VertexBuffer_h__
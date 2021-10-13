#include "component.h"

#include "../Shaders/vertex.h"
#include "../error.h"

void Component::Init(ID3D11Device* device, ID3D11DeviceContext* deviceContext)
{
	this->device = device;
	this->deviceContext = deviceContext;
	
	Vertex vertexes[] =
	{
		Vertex(-0.5f, -0.5f, 1.f, 1.f, 0.f, 0.f),
		Vertex(-0.5f, 0.5f, 1.f, 0.f, 1.f, 0.f),
		Vertex(0.5f, 0.5f, 1.f, 0.f, 0.f, 1.f),
		Vertex(0.5f, -0.5f, 1.f, 0.f, 1.0f, 1.0f),
	};

	DWORD indices[] =
	{
		0, 1, 2,
		0, 2, 3
	};
	
	CreateBuffer(&vertexBuffer, vertexes, sizeof(Vertex) * ARRAYSIZE(vertexes), D3D11_BIND_VERTEX_BUFFER);
	CreateBuffer(&indexBuffer, indices, sizeof(DWORD) * ARRAYSIZE(indices), D3D11_BIND_INDEX_BUFFER);
	CreateBuffer(&constantBuffer, nullptr, 16, D3D11_BIND_CONSTANT_BUFFER, D3D11_USAGE_DYNAMIC, D3D11_CPU_ACCESS_WRITE);
}

void Component::Draw(float time)
{
	UINT stride = sizeof(Vertex);
	UINT offset = 0;
	
	ApplyTransform(time);

	deviceContext->VSSetConstantBuffers(0, 1, &constantBuffer);
	deviceContext->IASetVertexBuffers(0, 1, &vertexBuffer, &stride, &offset);
	deviceContext->IASetIndexBuffer(indexBuffer, DXGI_FORMAT_R32_UINT, 0);

	deviceContext->DrawIndexed(6, 0, 0);
}

void Component::ApplyTransform(float angle)
{
	CB_VS_RotationMatrix matrix =
	{
		cos(angle), -sin(angle), 0.f, 0.f,
		sin(angle), cos(angle), 0.f, 0.f,
		0.f, 0.f, 1.f, 0.f,
		0.f, 0.f, 0.f, 1.f
	};

	CreateBuffer(&constantBuffer, &matrix, sizeof(matrix), D3D11_BIND_CONSTANT_BUFFER, D3D11_USAGE_DYNAMIC, D3D11_CPU_ACCESS_WRITE);
}

void Component::CreateBuffer(ID3D11Buffer** bufferAddress, const void* bufferDataArray, UINT byteWidth, UINT bindFlags,
	D3D11_USAGE usage, UINT CPUAccessFlags)
{
	D3D11_BUFFER_DESC bufferDesc;
	ZeroMemory(&bufferDesc, sizeof(bufferDesc));

	bufferDesc.Usage = usage;
	bufferDesc.ByteWidth = byteWidth;
	bufferDesc.BindFlags = bindFlags;
	bufferDesc.CPUAccessFlags = CPUAccessFlags;
	bufferDesc.MiscFlags = 0;
	bufferDesc.StructureByteStride = 0;

	HRESULT hr;
	if (bufferDataArray != nullptr)
	{
		D3D11_SUBRESOURCE_DATA bufferData;
		ZeroMemory(&bufferData, sizeof(bufferData));

		bufferData.pSysMem = bufferDataArray;
		//bufferData.SysMemPitch = 0;
		//bufferData.SysMemSlicePitch = 0;

		hr = device->CreateBuffer(&bufferDesc, &bufferData, bufferAddress);
	}
	else
	{
		hr = device->CreateBuffer(&bufferDesc, 0, bufferAddress);
	}

	popAssert(FAILED(hr), hr);
}
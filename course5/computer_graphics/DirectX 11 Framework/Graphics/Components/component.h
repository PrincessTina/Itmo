#pragma once
#include <d3d11.h>
#include <DirectXMath.h>

class Component
{
private:
	struct WorldPosition
	{
		float x = 0.f;
		float y = 0.f;
		float z = 0.f;
	};

	struct Color
	{
		float r = 0.f;
		float g = 0.f;
		float b = 0.f;
	};

public:
	WorldPosition worldPosition;
	Color color;
	
	void Init(ID3D11Device* device, ID3D11DeviceContext* deviceContext, float viewHeight);
	void Draw(float time);

private:
	struct CB_VS_RotationMatrix
	{
		DirectX::XMMATRIX matrix;
	};

	ID3D11Device* device;
	ID3D11DeviceContext* deviceContext;

	ID3D11Buffer* vertexBuffer;
	ID3D11Buffer* indexBuffer;
	ID3D11Buffer* constantBuffer;

	float viewHeight;

	void ApplyTransform(float angle);
	void CreateBuffer(ID3D11Buffer** bufferAddress, const void* bufferDataArray, UINT byteWidth, UINT bindFlags,
		D3D11_USAGE usage = D3D11_USAGE_DEFAULT, UINT CPUAccessFlags = 0);
};

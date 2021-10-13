#pragma once
#include <d3d11.h>

class Component
{
public:
	void Init(ID3D11Device* device, ID3D11DeviceContext* deviceContext);
	void Draw(float time);
private:
	struct CB_VS_RotationMatrix
	{
		struct
		{
			float matrix[4][4];
		} rotationMatrix;
	};

	ID3D11Device* device;
	ID3D11DeviceContext* deviceContext;

	ID3D11Buffer* vertexBuffer;
	ID3D11Buffer* indexBuffer;
	ID3D11Buffer* constantBuffer;

	void ApplyTransform(float angle);
	void CreateBuffer(ID3D11Buffer** bufferAddress, const void* bufferDataArray, UINT byteWidth, UINT bindFlags,
		D3D11_USAGE usage = D3D11_USAGE_DEFAULT, UINT CPUAccessFlags = 0);
};

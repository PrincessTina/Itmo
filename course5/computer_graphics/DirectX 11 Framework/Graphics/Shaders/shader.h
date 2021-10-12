#pragma once=
#include <d3d11.h>
#include <d3dcompiler.h>

class VertexShader
{
public:
	void Init(ID3D11Device* device, LPCWSTR pathToShader, D3D11_INPUT_ELEMENT_DESC* layoutDesc, UINT elementsCount);
	inline ID3D11VertexShader* GetShader() { return shader; };
	inline ID3D10Blob* GetBuffer() { return shaderBuffer; };
	inline ID3D11InputLayout* GetInputLayout() { return inputLayout; };
private:
	ID3D11VertexShader* shader;
	ID3D10Blob* shaderBuffer;
	ID3D11InputLayout* inputLayout;
};

class PixelShader
{
public:
	void Init(ID3D11Device* device, LPCWSTR pathToShader);
	inline ID3D11PixelShader* GetShader() { return shader; };
	inline ID3D10Blob* GetBuffer() { return shaderBuffer; };
private:
	ID3D11PixelShader* shader;
	ID3D10Blob* shaderBuffer;
};

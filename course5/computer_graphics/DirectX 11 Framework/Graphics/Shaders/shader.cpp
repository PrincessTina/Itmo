#include "shader.h"

#include "../error.h"

void VertexShader::Init(ID3D11Device* device, LPCWSTR pathToShader, D3D11_INPUT_ELEMENT_DESC* layoutDesc, UINT elementsCount)
{
	HRESULT hr = D3DReadFileToBlob(pathToShader, &shaderBuffer);
	popAssert(FAILED(hr), hr);

	hr = device->CreateVertexShader(shaderBuffer->GetBufferPointer(), shaderBuffer->GetBufferSize(), NULL, &shader);
	popAssert(FAILED(hr), hr);

	hr = device->CreateInputLayout(layoutDesc, elementsCount, shaderBuffer->GetBufferPointer(), shaderBuffer->GetBufferSize(), &inputLayout);
	popAssert(FAILED(hr), hr);
}

void PixelShader::Init(ID3D11Device* device, LPCWSTR pathToShader)
{
	HRESULT hr = D3DReadFileToBlob(pathToShader, &shaderBuffer);
	popAssert(FAILED(hr), hr);

	hr = device->CreatePixelShader(shaderBuffer->GetBufferPointer(), shaderBuffer->GetBufferSize(), NULL, &shader);
	popAssert(FAILED(hr), hr);
}
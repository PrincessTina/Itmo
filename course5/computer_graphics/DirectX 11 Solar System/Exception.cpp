#include "Exception.h"
#include <sstream>


MeMoriException::MeMoriException(int line, const char* file) noexcept
	:
	line(line),
	file(file)
{}

const char* MeMoriException::what() const noexcept
{
	std::ostringstream oss;
	oss << GetType() << std::endl << GetOriginString();
	whatBuffer = oss.str();
	return whatBuffer.c_str();
}

const char* MeMoriException::GetType() const noexcept
{
	return "Exception";
}

int MeMoriException::GetLine() const noexcept
{
	return line;
}

const std::string& MeMoriException::GetFile() const noexcept
{
	return file;
}

std::string MeMoriException::GetOriginString() const noexcept
{
	std::ostringstream oss;
	oss << "[File]" << file << std::endl << "[Line]" << line;
	return oss.str();
}
variable "vpc_cidr" {
  type        = string
  description = "The CIDR block for the VPC"
}

variable "vpc_name" {
  type        = string
  description = "The name of the VPC"
}

variable "public_subnet_cidr" {
  type        = string
  description = "The CIDR block for the public subnet"
}

variable "private_subnet_cidr" {
  type        = string
  description = "The CIDR block for the private subnet"
}

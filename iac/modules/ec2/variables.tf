variable "instance_type" {
  type        = string
  default     = "t2.micro"
  description = "The instance type for the EC2 instance"
}

variable "instance_name" {
  type        = string
  description = "The name tag for the EC2 instance"
}

variable "instance_profile_role" {
  type        = string
}

variable "subnet_id" {
  type        = string
  description = "The ID of the subnet where the EC2 instance will be launched"
}

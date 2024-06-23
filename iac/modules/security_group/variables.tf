variable "name" {
  type        = string
  description = "The name of the security group"
}

variable "description" {
  type        = string
  description = "The description of the security group"
}

variable "vpc_id" {
  type        = string
  description = "The ID of the VPC to associate the security group with"
}

variable "ingress_rules" {
  type = list(object({
    port     = number
    protocol = string
    cidr_blocks = list(string)
  }))
  description = "List of ingress rules for the security group"
}

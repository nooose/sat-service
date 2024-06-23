variable "role_name" {
  type        = string
  description = "The name of the IAM role"
}

variable "assume_role_policy" {
  type        = string
  description = "The assume role policy for the IAM role"
}

variable "policy_arn" {
  type        = string
  description = "The ARN of the policy to attach to the IAM role"
}

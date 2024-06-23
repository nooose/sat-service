output "security_group_id" {
  value       = aws_security_group.sg.id
  description = "The ID of the created security group"
}

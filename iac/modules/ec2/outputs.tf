output "instance_id" {
  value       = aws_instance.ec2.id
  description = "The ID of the EC2 instance"
}

output "instance_public_ip" {
  value       = aws_instance.ec2.public_ip
  description = "The public IP of the EC2 instance"
}

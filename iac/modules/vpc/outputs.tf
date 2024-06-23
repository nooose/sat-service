output "vpc_id" {
  value = aws_vpc.main.id
}

output "vpc_name" {
  value = aws_vpc.main.tags.Name
}

output "public_subnet_id" {
  value = aws_subnet.public.id
}

output "private_subnet_id" {
  value = aws_subnet.private.id
}

output "nat_gateway_id" {
  value = aws_nat_gateway.nat.id
}

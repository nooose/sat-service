resource "aws_iam_instance_profile" "ssm_instance_profile" {
  name = "${var.instance_profile_role}-profile"
  role = var.instance_profile_role
}

resource "aws_instance" "ec2" {
  ami = "ami-0edc5427d49d09d2a"
  instance_type = var.instance_type
  iam_instance_profile = aws_iam_instance_profile.ssm_instance_profile.name
  subnet_id = var.subnet_id

  tags = {
    Name = "${var.instance_name}-ec2"
  }
}

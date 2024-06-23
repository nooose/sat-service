resource "aws_iam_role" "role" {
  name               = "${var.role_name}-role"
  assume_role_policy = var.assume_role_policy
}

resource "aws_iam_role_policy_attachment" "policy_attachment" {
  role       = aws_iam_role.role.name
  policy_arn = var.policy_arn
}

provider "aws" {
  profile = "default"
  region  = "ap-northeast-2"
}

resource "aws_s3_bucket" "terraform_bucket" {
  bucket = "prd-sat-state"
}

terraform {
  backend "s3" {
    bucket = "prd-sat-state"
    key    = "terraform.tfstate"
    region = "ap-northeast-2"
  }
}

# __      _______   _____
# \ \    / /  __ \ / ____|
#  \ \  / /| |__) | |
#   \ \/ / |  ___/| |
#    \  /  | |    | |____
#     \/   |_|     \_____|
#

module "vpc" {
  source              = "./modules/vpc"
  vpc_cidr            = "10.0.0.0/16"
  vpc_name            = "prd-sat"
  public_subnet_cidr  = "10.0.10.0/24"
  private_subnet_cidr = "10.0.20.0/24"
}

#  _____          __  __
# |_   _|   /\   |  \/  |
#   | |    /  \  | \  / |
#   | |   / /\ \ | |\/| |
#  _| |_ / ____ \| |  | |
# |_____/_/    \_\_|  |_|
#

module "ssm_iam_role" {
  source    = "./modules/iam_role"
  role_name = "prd-sat-ssm"
  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Principal = {
          Service = "ec2.amazonaws.com"
        },
        Effect = "Allow",
        Sid    = ""
      },
    ],
  })
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}


#  ______ _____ ___
# |  ____/ ____|__ \
# | |__ | |       ) |
# |  __|| |      / /
# | |___| |____ / /_
# |______\_____|____|
#

module "web_security_group" {
  source = "./modules/security_group"

  name        = "prd-sat-public"
  description = "Security group for public"
  vpc_id      = module.vpc.vpc_id

  ingress_rules = [
    {
      port     = 80
      protocol = "tcp"
      cidr_blocks = ["0.0.0.0/0"]
    },
    {
      port     = 443
      protocol = "tcp"
      cidr_blocks = ["0.0.0.0/0"]
    },
  ]
}

module "web_ec2" {
  source                = "./modules/ec2"
  instance_type         = "t2.micro"
  instance_name         = "prd-sat-web"
  instance_profile_role = module.ssm_iam_role.role_name
  subnet_id             = module.vpc.public_subnet_id
  security_group_id     = module.web_security_group.security_group_id
}

#             _               _
#            | |             | |
#  ___  _   _| |_ _ __  _   _| |_ ___
# / _ \| | | | __| '_ \| | | | __/ __|
# | (_) | |_| | |_| |_) | |_| | |_\__ \
# \___/ \__,_|\__| .__/ \__,_|\__|___/
#                 | |
#                 |_|

output "vpc_id" {
  value = module.vpc.vpc_id
}

output "vpc_name" {
  value = module.vpc.vpc_name
}

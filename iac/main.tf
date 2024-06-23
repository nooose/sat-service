provider "aws" {
  profile = "default"
  region = "ap-northeast-2"
}

resource "aws_s3_bucket" "terraform_bucket" {
  bucket = "prd-sat-state"
}

terraform {
  backend "s3" {
    bucket = "prd-sat-state"
    key = "s3-backend/terraform.tfstate"
    region = "ap-northeast-2"
  }
}

module "vpc" {
  source             = "./modules/vpc"
  vpc_cidr           = "10.0.0.0/16"
  vpc_name           = "prd-sat"
  public_subnet_cidr = "10.0.10.0/24"
  private_subnet_cidr = "10.0.20.0/24"
}

output "vpc_id" {
  value = module.vpc.vpc_id
}

output "vpc_name" {
  value = module.vpc.vpc_name
}

output "public_subnet_id" {
  value = module.vpc.public_subnet_id
}

output "private_subnet_id" {
  value = module.vpc.private_subnet_id
}

output "nat_gateway_id" {
  value = module.vpc.nat_gateway_id
}

#version 330 core

out vec4 FragColor;

in vec4 myColors;
in vec2 outTexture;

uniform sampler2D epicTexture;

void main()
{
    FragColor = texture(epicTexture, outTexture);
}
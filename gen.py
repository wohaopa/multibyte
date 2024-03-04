from PIL import Image, ImageDraw, ImageFont

def text_to_pixels(index, font_size=12):
    # Font settings
    font_path = "simsunb.ttf"
    font = ImageFont.truetype(font_path, font_size)

    # Create image
    image = Image.new("RGBA", (16 * 16, 16 * 16), color=(255, 255, 255, 0))
    draw = ImageDraw.Draw(image)

    for i in range(0, 16):
        for j in range(0, 16):
            x = 16 * i
            y = 16 * j
            id = index*16*16 + i*16 + j
            draw.text((y, x), chr(id), font=font, fill="black")

    image.save("src/main/resources/assets/minecraft/textures/font/unicode_page_" + hex(index)[2:] + ".png")


for i in range(0x200, 0x2a7):
    text_to_pixels(i, font_size=16)

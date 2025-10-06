package com.juha.photoscala

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


// red/green/blue between 0-255
case class Pixel(r:Int, g:Int, b:Int) {
  assert(r >= 0 && r < 256 && g >= 0 && g < 256 && b >= 0 && b < 256)

  def toInt: Int =
    // shift position of hex
    (r << 16) | (g << 8) | b

  infix def +(other:Pixel): Pixel =
    Pixel(
      Pixel.clamp(r + other.r),
      Pixel.clamp(g + other.g),
      Pixel.clamp(b + other.b))
  def draw(w: Int, h:Int, path:String) = {
    val color = toInt
    val image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
    val pixels = Array.fill(w * h)(color)
    image.setRGB(0,0,w,h, pixels, 0, w)
    ImageIO.write(image, "JPG", new File(path))
  }
}

object Pixel {
  val BLACK = Pixel(0,0,0)
  val WHITE = Pixel(255,255,255)
  val BLUE = Pixel(0,0,255)
  
//  check that values are between 0-255
  def clamp(v:Int): Int = {
    if v <= 0 then 0
    else if v >= 255 then 255
    else v
  }

  def main(args: Array[String]): Unit = {
    val red = Pixel(150,0,0)
    val green = Pixel(0,255,0)
    val yellow = Pixel(255,255,0)
    red.draw(40,40,"photoscala/src/main/resources/pixels/reg.jpg")
    yellow.draw(40,40,"photoscala/src/main/resources/pixels/yellow.jpg")
  }
}
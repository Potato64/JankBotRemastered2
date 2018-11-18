package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

public class PixyCam
{
    private I2cDeviceSynch pixy;

    public Block[] blockList;

    private int blockIndex;

    public PixyCam(I2cDeviceSynch pixy)
    {
        this.pixy = pixy;

        pixy.setI2cAddress(I2cAddr.create7bit(0x54));

        pixy.setReadWindow(new I2cDeviceSynch.ReadWindow (1, 26, I2cDeviceSynch.ReadMode.REPEAT));

        pixy.engage();

        blockList = new Block[135];
        blockIndex = 0;
    }

    public double firstBlockAngle()
    {
        //TODO finish this thing
        return 0;
    }

    public void getObjects()
    {
        getObject();

        while (blockIndex > 0)
        {
            getObject();
        }
    }

    public void getObject()
    {
        int checksum;
        int signature;
        int x;
        int y;
        int width;
        int height;

        byte data1 = pixy.read8(0);

        if ((data1 & 0xff) != 0x55 && (data1 & 0xff) != 0x56) //if it does not see an object
        {
            return;
        }

        byte data2 = pixy.read8(0);

        if ((data2 & 0xff) == 0xaa) //second part of sync word
        {
            data1 = pixy.read8(0);
            data2 = pixy.read8(0);

            if ((data1 & 0xff) == 0x55 && (data2 & 0xff) == 0xaa) //if next part is another sync word, this is the start of the packet
            {
                blockIndex = 0;

                data1 = pixy.read8(0);
                data2 = pixy.read8(0);

                checksum = (data1 & 0xff) + ((data2 & 0xff) << 8); //gets the checksum
            }
            else //otherwise, this is another object in an already read packet
            {
                blockIndex++;

                checksum = (data1 & 0xff) + ((data2 & 0xff) << 8); //gets the checksum
            }

            data1 = pixy.read8(0);
            data2 = pixy.read8(0);
            signature = (data1 & 0xff) + ((data2 & 0xff) << 8); //color signature of block

            data1 = pixy.read8(0);
            data2 = pixy.read8(0);
            x = (data1 & 0xff) + ((data2 & 0xff) << 8); //x position of block

            data1 = pixy.read8(0);
            data2 = pixy.read8(0);
            y = (data1 & 0xff) + ((data2 & 0xff) << 8); //y position of block

            data1 = pixy.read8(0);
            data2 = pixy.read8(0);
            width = (data1 & 0xff) + ((data2 & 0xff) << 8); //width of block

            data1 = pixy.read8(0);
            data2 = pixy.read8(0);
            height = (data1 & 0xff) + ((data2 & 0xff) << 8); //height of block

            if (signature + x + y + width + height == checksum) //verifying data with checksum
            {
                blockList[blockIndex] = new Block(signature, x, y, width, height); //update blockList
            }
        }
    }

    public static class Block
    {
        public int signature;
        public int x;
        public int y;
        public int width;
        public int height;

        private Block(int signature, int x, int y, int width, int height)
        {
            this.signature = signature;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Override
        public String toString()
        {
            return "Signature: " + signature + "\nX: " + x + "\nY: " + width + "\nHeight: " + height;
        }
    }
}

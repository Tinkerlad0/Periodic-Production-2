package com.tinkerlad.chemistry2.util;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class DevUtils {

    public static void dumpBlockNames() {

        try {
            File file = new File("C:\\temp\\PPDump.csv");
            //file.mkdirs();
            //file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);

            getBlockNames(fileWriter);
            getItemNames(fileWriter);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void getItemNames(FileWriter fileWriter) throws IOException {
        Iterator<Item> itemIterator = Item.itemRegistry.iterator();

        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            String[] list = new String[3];
            list[0] = (item.getUnlocalizedName());
            list[1] = I18n.format(item.getUnlocalizedName() + ".name").replaceAll("(.name)", "");
            list[2] = list[0].equalsIgnoreCase(list[1]) ? "true" : "false";
            appendInfo(fileWriter, list);
        }
    }

    private static void appendInfo(FileWriter writer, String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            writer.append(args[i]);
            writer.append(',');
        }
        writer.append('\n');
    }

    private static void getBlockNames(FileWriter fileWriter) throws IOException {
        Iterator<Block> blockIterator = Block.blockRegistry.iterator();

        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            String[] list = new String[3];
            list[0] = (block.getUnlocalizedName());
            list[1] = (I18n.format(block.getUnlocalizedName() + ".name")).replaceAll("(.name)", "");
            list[2] = list[0].equalsIgnoreCase(list[1]) ? "true" : "false";
            appendInfo(fileWriter, list);
        }
    }
}

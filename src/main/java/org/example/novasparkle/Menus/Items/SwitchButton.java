package org.example.novasparkle.Menus.Items;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.example.novasparkle.Menus.Menu;
import org.example.novasparkle.Utils.Utils;

import java.util.Objects;


public class SwitchButton extends Item {
    @Getter
    private final SwitchType action;
    public SwitchButton(ConfigurationSection section) {
        super(section);
        this.setSlot((byte) Utils.getIndex(section.getInt("slot.row"), section.getInt("slot.column")));
        this.action = SwitchType.valueOf(Objects.requireNonNull(section.getString("action"))
                .toUpperCase());
    }

    private enum SwitchType {
        NEXT {
            @Override
            void mSwitch(Menu menu) {
                int page = menu.getPage();
                if (page != menu.getMenuSize())
                    menu.setPage((byte) (page + 1));
            }
        },
        PREVIOUS {
            @Override
            void mSwitch(Menu menu) {
                int page = menu.getPage();
                if (page != 1) {
                    menu.setPage((byte) (page - 1));
                }
            }
        };

//        @Override
//        public String toString() {
//            return this.name();
//        }

        abstract void mSwitch(Menu menu);
    }
    public String getAction() {
        return this.action.name();
    }
    public void click(Menu menu) {
        this.action.mSwitch(menu);
    }
}

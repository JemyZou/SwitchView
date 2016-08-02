# SwitchView
This is my own definition SwitchView
开关默认关闭，获取开关的状态代码如下所示：
 switchView = (SwitcherView) findViewById(R.id.switch_view);
        switchView.setOnSwitchListener(new OnSwitchListener() {
            @Override
            public void onSwitch(boolean isOpened) {
                Toast.makeText(MainActivity.this,isOpened?"打开":"关闭",Toast.LENGTH_SHORT).show();
            }
        });

/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * 
 * Generated with the TypeScript template
 * https://github.com/emin93/react-native-template-typescript
 * 
 * @format
 */

import React, {Component} from 'react';
import ThetaPlugin from './theta';
import { Container, Header, Content, Button, Text, Tab, Tabs, ScrollableTab } from 'native-base';
import LedPage from './component/LedPage';
import OtherPage from './component/OtherPage';
import SoundPage from './component/SoundPage';

interface Props {}
export default class App extends Component<Props> {
    constructor(props: any) {
        super(props);
    }
    async componentDidMount() {
        ThetaPlugin.addListener('BUTTON_LONG_PRESS', (e: Event) => {
            console.log("BUTTON_LONG_PRESS", e);
        });
        ThetaPlugin.addListener('BUTTON_PUSH_UP', (e: Event) => {
            console.log("BUTTON_PUSH_UP", e);
        });
        ThetaPlugin.addListener('BUTTON_PUSH_DOWN', (e: Event) => {
            console.log("BUTTON_PUSH_DOWN", e);
        });

        ThetaPlugin.addListener('MEDIA_RECORD_BUTTON_PUSH_DOWN', (e: Event) => {
            console.log("MEDIA_RECORD_BUTTON_PUSH_DOWN", e);
        });
        ThetaPlugin.addListener('MEDIA_RECORD_BUTTON_PUSH_DOWN', (e: Event) => {
            console.log("MEDIA_RECORD_BUTTON_PUSH_DOWN", e);
        });
        ThetaPlugin.addListener('WLAN_ON_OFF_BUTTON_PUSH_DOWN', (e: Event) => {
            console.log("WLAN_ON_OFF_BUTTON_PUSH_DOWN", e);
        });

        ThetaPlugin.addListener('CAMERA_BUTTON_PUSH_UP', (e: Event) => {
            console.log("CAMERA_BUTTON_PUSH_UP", e);
        });
        ThetaPlugin.addListener('MEDIA_RECORD_BUTTON_PUSH_UP', (e: Event) => {
            console.log("MEDIA_RECORD_BUTTON_PUSH_UP", e);
        });
        ThetaPlugin.addListener('WLAN_ON_OFF_BUTTON_PUSH_UP', (e: Event) => {
            console.log("WLAN_ON_OFF_BUTTON_PUSH_UP", e);
        });

        ThetaPlugin.addListener('CAMERA_BUTTON_LONG_PRESS', (e: Event) => {
            console.log("CAMERA_BUTTON_LONG_PRESS", e);
        });
        ThetaPlugin.addListener('MEDIA_RECORD_BUTTON_LONG_PRESS', (e: Event) => {
            console.log("MEDIA_RECORD_BUTTON_LONG_PRESS", e);
        });
        ThetaPlugin.addListener('WLAN_ON_OFF_BUTTON_LONG_PRESS', (e: Event) => {
            console.log("WLAN_ON_OFF_BUTTON_LONG_PRESS", e);
        });
    }
    render() {
        return (
            <Container>
                <Tabs renderTabBar={()=> <ScrollableTab />}>
                    <Tab heading="Sound">
                        <SoundPage/>
                    </Tab>
                    <Tab heading="LED">
                        <LedPage/>
                    </Tab>
                    <Tab heading="OTHER">
                        <OtherPage/>
                    </Tab>
                </Tabs>
            </Container>
        );
    }
}

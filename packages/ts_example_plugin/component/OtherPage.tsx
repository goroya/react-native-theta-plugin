import React, {Component} from 'react';
import ThetaPlugin from '../theta';
import {Container, Header, Content, Button, Text, Tab, Tabs, ScrollableTab, View} from 'native-base';
import {StyleSheet} from "react-native";

interface Props {}
export default class OtherPage extends Component<Props> {
    constructor(props: any) {
        super(props);
        this.state = {isToggleOn: true};

        this.otherClick = this.otherClick.bind(this);
    }
    async otherClick(soundName: string) {
        switch (soundName) {
            case 'WlanOff':
                await ThetaPlugin.notificationWlanOff();
                break;
            case 'WlanAp':
                await ThetaPlugin.notificationWlanAp();
                break;
            case 'WlanCl':
                await ThetaPlugin.notificationWlanCl();
                break;
            case 'DatabaseUpdate':
                await ThetaPlugin.notificationDatabaseUpdate([]);
                break;
            case 'Success':
                await ThetaPlugin.notificationSuccess();
                break;
            case 'Error':
                await ThetaPlugin.notificationError('Error Hello');
                break;
            case 'ErrorOccured':
                await ThetaPlugin.notificationErrorOccured();
                break;
            case 'AutoCloseTrue':
                console.log('AutoCloseTrue');
                await ThetaPlugin.setAutoClose(true);
                break;
            case 'AutoCloseFalse':
                console.log('AutoCloseFalse');
                await ThetaPlugin.setAutoClose(false);
                break;
            case 'Close':
                console.log('close');
                await ThetaPlugin.close();
                break;
            case 'CameraOpen':
                console.log('CameraOpen');
                await ThetaPlugin.notificationCameraOpen();
                break;
            case 'CameraClose':
                console.log('CameraClose');
                await ThetaPlugin.notificationCameraClose();
                break;
        }
    }
    async componentDidMount() {
        const data = await ThetaPlugin.notificationAudioSelf().catch((e: any) => e);
        console.log(data);
    }
    render() {
        return (
            <Container>
                <Content>
                    <Button dark small style={styles.button} onPress={() => { this.otherClick("WlanOff") }}>
                        <Text>notificationWlanOff</Text>
                    </Button>
                    <Button primary small style={styles.button} onPress={() => { this.otherClick("WlanAp") }}>
                        <Text>notificationWlanAp</Text>
                    </Button>
                    <Button success small style={styles.button} onPress={() => { this.otherClick("WlanCl") }}>
                        <Text>notificationWlanCl</Text>
                    </Button>
                    <Button info small style={styles.button} onPress={() => { this.otherClick("DatabaseUpdate") }}>
                        <Text>notificationDatabaseUpdate</Text>
                    </Button>
                    <Button warning small style={styles.button} onPress={() => { this.otherClick("Success") }}>
                        <Text>notificationSuccess</Text>
                    </Button>
                    <Button danger small style={styles.button} onPress={() => { this.otherClick("Error") }}>
                        <Text>notificationError</Text>
                    </Button>
                    <Button light small style={styles.button} onPress={() => { this.otherClick("ErrorOccured") }}>
                        <Text>notificationErrorOccured</Text>
                    </Button>
                    <Button primary small style={styles.button} onPress={() => { this.otherClick("AutoCloseTrue") }}>
                        <Text>AutoCloseTrue</Text>
                    </Button>
                    <Button success small style={styles.button} onPress={() => { this.otherClick("AutoCloseFalse") }}>
                        <Text>AutoCloseFalse</Text>
                    </Button>
                    <Button success small style={styles.button} onPress={() => { this.otherClick("Close") }}>
                        <Text>Close</Text>
                    </Button>
                    <Button success small style={styles.button} onPress={() => { this.otherClick("CameraOpen") }}>
                        <Text>Camera Open</Text>
                    </Button>
                    <Button success small style={styles.button} onPress={() => { this.otherClick("CameraClose") }}>
                        <Text>Camera Close</Text>
                    </Button>
                </Content>
            </Container>
        );
    }
}

const styles = StyleSheet.create({
    button: {
        margin: 3
    },
});

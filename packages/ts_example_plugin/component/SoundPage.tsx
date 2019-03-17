import React, {Component} from 'react';
import ThetaPlugin from '../theta';
import {Container, Header, Content, Button, Text, Tab, Tabs, ScrollableTab, View} from 'native-base';
import {StyleSheet} from "react-native";

interface Props {}
export default class SoundPage extends Component<Props> {
    constructor(props: any) {
        super(props);
        this.state = {isToggleOn: true};

        this.soundClick = this.soundClick.bind(this);
    }
    async soundClick(soundName: string) {
        switch (soundName) {
            case 'AudioShutter':
                console.log("hello");
                await ThetaPlugin.notificationAudioShutter();
                break;
            case 'AudioOpen':
                console.log("hello");
                await ThetaPlugin.notificationAudioOpen();
                break;
            case 'AudioClose':
                console.log("hello");
                await ThetaPlugin.notificationAudioClose();
                break;
            case 'AudioMovStart':
                console.log("hello");
                await ThetaPlugin.notificationAudioMovStart();
                break;
            case 'AudioMovStop':
                console.log("hello");
                await ThetaPlugin.notificationAudioMovStop();
                break;
            case 'AudioSelf':
                console.log("hello");
                await ThetaPlugin.notificationAudioSelf();
                break;
            case 'AudioWarning':
                console.log("hello");
                await ThetaPlugin.notificationAudioWarning();
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
                    <Button dark small style={styles.button} onPress={() => { this.soundClick("AudioShutter") }}>
                        <Text>AudioShutter</Text>
                    </Button>
                    <Button primary small style={styles.button} onPress={() => { this.soundClick("AudioOpen") }}>
                        <Text>AudioOpen</Text>
                    </Button>
                    <Button success small style={styles.button} onPress={() => { this.soundClick("AudioClose") }}>
                        <Text>AudioClose</Text>
                    </Button>
                    <Button info small style={styles.button} onPress={() => { this.soundClick("AudioMovStart") }}>
                        <Text>AudioMovStart</Text>
                    </Button>
                    <Button warning small style={styles.button} onPress={() => { this.soundClick("AudioMovStop") }}>
                        <Text>AudioMovStop</Text>
                    </Button>
                    <Button danger small style={styles.button} onPress={() => { this.soundClick("AudioSelf") }}>
                        <Text>AudioSelf</Text>
                    </Button>
                    <Button light small style={styles.button} onPress={() => { this.soundClick("AudioWarning") }}>
                        <Text>AudioWarning</Text>
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
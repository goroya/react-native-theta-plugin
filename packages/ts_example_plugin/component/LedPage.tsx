import React, {Component} from 'react';
import ThetaPlugin from '../theta';
import {
    Container,
    Header,
    Content,
    Button,
    Text,
    Tab,
    Tabs,
    ScrollableTab,
    View,
    Picker,
    Input,
    Item
} from 'native-base';
import {StyleSheet} from "react-native";

interface Props {}
export default class LedPage extends Component<Props, any> {
    constructor(props: any) {
        super(props);
        this.state = {
            led: "LED3",
            color: "red",
            interval: 300
        };
        this.buttonClick = this.buttonClick.bind(this);
    }
    onValueChangeLed(value: string) {
        this.setState({
            led: value
        });
    }
    onValueChangeColor(value: string) {
        this.setState({
            color: value
        });
    }
    async buttonClick(ledMethod: string) {
        switch (ledMethod) {
            case 'Led3Show':
                await ThetaPlugin.notificationLed3Show(this.state.color);
                break;
            case 'LedShow':
                await ThetaPlugin.notificationLedShow(this.state.led);
                break;
            case 'LedBlink':
                await ThetaPlugin.notificationLedBlink(this.state.led, this.state.color, parseInt(this.state.interval));
                break;
            case 'LedHide':
                console.log("hello");
                await ThetaPlugin.notificationLedHide(this.state.led);
                break;
        }
    }
    componentDidMount() {
    }
    render() {
        return (
            <Container>
                <Content>
                    <Picker
                        note
                        mode="dropdown"
                        style={{ width: 120 }}
                        selectedValue={this.state.led}
                        onValueChange={this.onValueChangeLed.bind(this)}
                    >
                        <Picker.Item label="LED3" value="LED3" />
                        <Picker.Item label="LED4" value="LED4" />
                        <Picker.Item label="LED5" value="LED5" />
                        <Picker.Item label="LED6" value="LED6" />
                        <Picker.Item label="LED7" value="LED7" />
                        <Picker.Item label="LED8" value="LED8" />
                    </Picker>
                    <Picker
                        note
                        mode="dropdown"
                        style={{ width: 120 }}
                        selectedValue={this.state.color}
                        onValueChange={this.onValueChangeColor.bind(this)}
                    >
                        <Picker.Item label="Red" value="red" />
                        <Picker.Item label="Green" value="green" />
                        <Picker.Item label="Blue" value="blue" />
                        <Picker.Item label="Cyan" value="cyan" />
                        <Picker.Item label="Magenta" value="magenta" />
                        <Picker.Item label="Yellow" value="yellow" />
                        <Picker.Item label="White" value="white" />
                    </Picker>
                    <Item regular>
                        <Input
                            onChangeText={
                                (text) => {
                                    if (/^\d+$/.test(text)) {
                                        this.setState({interval: text})
                                    }
                                }
                            }
                            keyboardType='numeric'
                            defaultValue="300"
                            placeholder='Interval' />
                    </Item>
                    <Button info small style={styles.button} onPress={() => { this.buttonClick("Led3Show") } }>
                        <Text>Led3Show</Text>
                    </Button>
                    <Button warning small style={styles.button} onPress={() => { this.buttonClick("LedShow") } }>
                        <Text>LedShow</Text>
                    </Button>
                    <Button danger small style={styles.button} onPress={() => { this.buttonClick("LedBlink") } }>
                        <Text>LedBlink</Text>
                    </Button>
                    <Button light small style={styles.button} onPress={() => { this.buttonClick("LedHide") } }>
                        <Text>LedHide</Text>
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

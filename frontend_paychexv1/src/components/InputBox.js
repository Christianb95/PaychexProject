import React, { Component } from 'react';
/* React Input box example */
class InputBoxExample extends Component {
    constructor() {

        super();
        this.state = {
            inputValue: '' // Initialize the input value as an empty string
        };
    }

    handleInputChange = (e) => {
        this.setState({ inputValue: e.target.value });
    }

    render() {
        return (
            <div>
                <h2>Input Box Example</h2>
                <input
                    type="text"
                    value={this.state.inputValue}
                    onChange={this.handleInputChange}
                />
                <p>You typed: {this.state.inputValue}</p>
            </div>
        );
    }
}

export default InputBoxExample;
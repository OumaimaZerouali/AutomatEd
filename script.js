document.getElementById('ideaForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const idea = document.getElementById('idea').value;


    fetch('https://api.github.com/repos/OumaimaZerouali/AutomatEd/issues', {
        method: 'POST',
        headers: {
            'Authorization': 'TOKEN',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            title: `New Idea: ${new Date().toLocaleString()}`,
            body: idea,
            labels: ['Ideas']
        })
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('response').innerText = 'Idea submitted successfully!';
            document.getElementById('idea').value = '';
        })
        .catch(error => {
            document.getElementById('response').innerText = 'Error submitting idea. Please try again.';
        });
});
